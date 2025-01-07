import puppeteer from "puppeteer";

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
  // - open the "http://quotes.toscrape.com/" website
  // - wait until the dom content is loaded (HTML is ready)
  await page.goto("https://aurora.umanitoba.ca/ssb/ksstransequiv.p_trans_eq_main", {
    waitUntil: "domcontentloaded",
  });

  await page.click("a[href=\"ksstransequiv.p_trans_eq_main?rpt_type=new\"]", {
    waitUntil: "domcontentloaded",
  });

  await Promise.all([
    page.click("#frmProvState > #p_selProvState > option[value=\"ALL\"]"),
    page.waitForNavigation(),
  ]);


  // get the list of instituitions 

  const instituitions = await page.evaluate(() => {
    const temp = document.getElementsByClassName('datadisplaytable')[0].getElementsByTagName('tr')[1].getElementsByTagName('td')[1].querySelectorAll('option');
    values = new Array;
    for(let i = 0; i < temp.length; i++)
      {
        values.push(temp[i].getAttribute('value'));
      }
    return values
  });


  // start loop here

  await Promise.all([
    page.click("#frmInstitution > #p_selInstitution > option[value=\"CMB022\"]"),
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


  console.log(instituitions[0] + "\n");
  console.log(instituitions[1] + "\n");
  console.log(instituitions[2] + "\n");

  await browser.close();

};

// Start the scraping
getQuotes();
