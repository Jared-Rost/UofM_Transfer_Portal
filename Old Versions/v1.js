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



  

  /* 

  v1 
  
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








  // get the table
  const courseTable = await page.evaluate(() => {
    const tds = Array.from(document.querySelectorAll('table tr td'))
    return tds.map(td => td.innerText)
  });


  requiredDiv = document.getElementsByTagName('table')[1];
  const allColumns = data.map(e => [e[0], e[1], e[2], e[3]]);
  console.table(allColumns.slice(0, 10));


   // parse it
  const data = await page.$$eval(courseTable, els =>
    els.slice(2).map(el =>
      [...el.querySelectorAll("td")]
        .map(e => e.textContent.trim())
    )
  );


  // select all relavent info 
  const courses = await page.evaluate(() => {

    // Fetch the first element with class "quote"
    // Get the displayed text and returns it
    const quoteList = document.querySelectorAll(".datadisplaytable > tr[]");
    // SELECT THE SECOND TABLE IN THE DOC and then get all 2nd+ TR or filter TR by style

    // Convert the quoteList to an iterable array
    // For each quote fetch the text and author
    return Array.from(quoteList).map((quote) => {
      // Fetch the sub-elements from the previously fetched quote element
      // Get the displayed text and return it (`.innerText`)
      const text = quote.querySelector(".text").innerText;
      const author = quote.querySelector(".author").innerText;

      return { text, author };
    });
  });


  
  
  
  await page.click("#frmProvState > #p_selProvState > option[value=\"ALL\"]", {
    waitUntil: "domcontentloaded",
  });
  
  setTimeout(function() {
    //your code to be executed after 10 second
  }, 1000000);

  await page.click("#frmInstitution > #p_selInstitution > option[value=\"CMB022\"]", {
    waitUntil: "domcontentloaded",
  });

  setTimeout(function() {
    //your code to be executed after 10 second
  }, 10000);

  await page.click("#frmSubject > #p_selSubject > option[value=\" ALL\"]", {
    waitUntil: "domcontentloaded",
  }); */
  

//form[name=\"frmProvState\"]
  /* await page.click(".datadisplaytable > #frmProvState > #p_selProvState > option[value='ALL']", {
    waitUntil: "domcontentloaded",
  });

  // Get page data
  const quotes = await page.evaluate(() => {

  // Fetch the first element with class "quote"
  // Get the displayed text and returns it
  const quoteList = document.querySelectorAll(".quote");

    // Convert the quoteList to an iterable array
    // For each quote fetch the text and author
    return Array.from(quoteList).map((quote) => {
      // Fetch the sub-elements from the previously fetched quote element
      // Get the displayed text and return it (`.innerText`)
      const text = quote.querySelector(".text").innerText;
      const author = quote.querySelector(".author").innerText;

      return { text, author };
    });
  });

  // Display the quotes
  console.log(quotes);

    // Click on the "Next page" button
    // await page.click(".pager > .next > a");

  // Close the browser
  await browser.close(); */

};

// Start the scraping
getQuotes();
