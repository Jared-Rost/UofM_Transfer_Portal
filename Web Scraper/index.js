import puppeteer from "puppeteer";
import * as fs from 'fs';

const getQuotes = async () => {
  // Start a Puppeteer session with:
  // - a visible browser (`headless: false` - easier to debug because you'll see the browser in action)
  // - no default viewport (`defaultViewport: null` - website page will be in full width and height)
  const browser = await puppeteer.launch({
    headless: false,
    defaultViewport: null,
  });

  // Open a new page
  const page = await browser.newPage();

  // On this new page:
  // - open the website
  // - wait until the dom content is loaded (HTML is ready)
  await page.goto("https://aurora.umanitoba.ca/ssb/ksstransequiv.p_trans_eq_main", {
    waitUntil: "domcontentloaded",
  });


  // this is for existing student
  await page.click("a[href=\"ksstransequiv.p_trans_eq_main?rpt_type=current\"]", {
    waitUntil: "domcontentloaded",
  });


  // this is for new student
  /* await page.click("a[href=\"ksstransequiv.p_trans_eq_main?rpt_type=new\"]", {
    waitUntil: "domcontentloaded",
  }); */

  await Promise.all([
    page.click("#frmProvState > #p_selProvState > option[value=\"ALL\"]"),
    page.waitForNavigation(),
  ]);


  // get the list of instituitions 
  const instituitions = await page.evaluate(() => {
    const temp = document.getElementsByClassName('datadisplaytable')[0].getElementsByTagName('tr')[1].getElementsByTagName('td')[1].querySelectorAll('option');
    let values = new Array();
    for(let i = 0; i < temp.length; i++)
      {
        values.push(temp[i].getAttribute('value'));
      }
    return values
  });


  // also get a list of the names of those institutions 
  const instituitionsDescriptions = await page.evaluate(() => {
    const temp = document.getElementsByClassName('datadisplaytable')[0].getElementsByTagName('tr')[1].getElementsByTagName('td')[1].querySelectorAll('option');
    let values = new Array();
    for(let i = 0; i < temp.length; i++)
      {
        values.push(temp[i].innerText);
      }
    return values
  });




  // start loop here
  // loop through all the values in the instituition column and collect them into seperate arrays
  let allCourses = new Array();
  for(let i = 0; i < instituitions.length; i++)
 //for(let i = 0; i < 10; i++)
  {

    // select the specific intituition from the list
    await Promise.all([
      page.click("#frmInstitution > #p_selInstitution > option[value=\""+ instituitions[i] +"\"]"),
      page.waitForNavigation(),
    ]);

    await Promise.all([
      page.click("#frmSubject > #p_selSubject > option[value=\" ALL\"]"),
      page.waitForNavigation(),
    ]);


    // get the table
    const courseTable = await page.evaluate(() => {
      const tds = Array.from(document.getElementsByClassName('datadisplaytable')[1].querySelectorAll('tr td'))
      return tds.map(td => td.innerText)
    });

    for(let j = 0; j < courseTable.length; j++)
      {
        courseTable[j] = courseTable[j].replace("\n", " ");
      }

    // push current table to list of all tables
    allCourses.push(courseTable);

  }



  const refinedData = []

  const columnNames = ['other instituition name', 'other instituition course', 'umanitoba course', 'comments', 'effective date']

  refinedData.push(columnNames);

  // now loop through each individual instituition 
  for(let i = 0; i < allCourses.length; i++)
    {
      // for each instituition loop through all courses associated with it
      // increase by 4 to correspond to each row
      for(let j = 0; j < (allCourses[i].length / 4); j = j+4)
        {
          const currentInstituitionData = [];
          currentInstituitionData.push(instituitionsDescriptions[i]);
          currentInstituitionData.push(allCourses[i][j]);
          currentInstituitionData.push(allCourses[i][j+1]);
          currentInstituitionData.push(allCourses[i][j+2]);
          currentInstituitionData.push(allCourses[i][j+3]);
          refinedData.push(currentInstituitionData);
        }
    }


    // now turn it all into a csv
    const finalCSV = arrayToCSV(refinedData);

    // Write data in to csv file
    fs.writeFile('Output.csv', finalCSV, (err) => {

      // In case of a error throw err.
      if (err) throw err;
  })
  
  //console.log(finalCSV);

  await browser.close();

};


const arrayToCSV = (arr, delimiter = ',') =>
  arr
    .map(v =>
      v.map(x => (isNaN(x) ? `"${x.replace(/"/g, '""')}"` : x)).join(delimiter)
    )
    .join('\n');

// Start the scraping
getQuotes();
