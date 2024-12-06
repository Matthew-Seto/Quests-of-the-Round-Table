
const { assert } = require('console');
const { Builder, By, until, WebElement } = require('selenium-webdriver');

module.exports = { scenario1, scenario2, scenario3, scenario4 };

// test.js
async function main() {
    const args = process.argv.slice(2);
    if (args.length === 0) {
        console.log("Please provide a scenario to run:");
        process.exit(1);
    }

    const scenario = args[0];

    switch (scenario) {
        case 'scenario1':
            await scenario1();
            break;
        case 'scenario2':
            await scenario2();
            break;
        case 'scenario3':
            await scenario3();
            break;
        case 'scenario4':
            await scenario4();
            break;
        default:
            console.log(`Unknown scenario: ${scenario}`);
            process.exit(1);
    }
}

main().catch(error => {
    console.error("An error occurred:", error);
    process.exit(1);
});


async function scenario1() {
    let driver = await new Builder().forBrowser('chrome').build();

    try{
        await driver.get('http://localhost:8080/');

        await driver.sleep(3000);

        let connectButton = await driver.findElement(By.xpath("//button[@id='connect']"));
        await connectButton.click();

        await driver.sleep(4000);

        let scenarioButton = await driver.findElement(By.xpath("//button[@id='scenario1']"));
        await scenarioButton.click();

        let textBox = await driver.findElement(By.id("name"));
        await textBox.click();
        await textBox.sendKeys("no");

        let sendButton = await driver.findElement(By.xpath("//button[@id='send']"));
        await driver.sleep(4000);
        sendButton.click();

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("yes");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("2");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("2");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("3");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("2");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("3");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("7");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("7");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("7");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("9");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("9");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("7");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("7");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        let finalStatus = await driver.findElement(By.id('main-content')).getText();
        console.log("Final Game Status:", finalStatus);

        if (finalStatus.includes("F5 F10 F15 F15 F30 H10 B15 B15 L20") && finalStatus.includes("F15 F20 F20 F25 H10 H10 H10 S10 B15 B15 E30 E30") && finalStatus.includes("F5 F5 F15 F30 S10") && finalStatus.includes("F15 F15 F40 L20")) {
            console.log("Test passed: Hands are displayed correctly.")
        } else {
            console.log("Test failed: Hands are displayed incorrectly.")
        }

        let pOneHandsize = await driver.findElement(By.xpath('//tbody/tr[1]/td[2]')).getText();
        let pTwoHandsize = await driver.findElement(By.xpath('//tbody/tr[2]/td[2]')).getText();
        let pThreeHandsize = await driver.findElement(By.xpath('//tbody/tr[3]/td[2]')).getText();
        let pFourHandsize = await driver.findElement(By.xpath('//tbody/tr[4]/td[2]')).getText();

        assert(pOneHandsize, 9)
        assert(pTwoHandsize, 12)
        assert(pThreeHandsize, 4)
        assert(pFourHandsize, 4)
    } catch (error) {
        console.error("Test encountered an error:", error);
    } finally {
        //await driver.quit();
    }
}

async function scenario2() {
    let driver = await new Builder().forBrowser('chrome').build();

    try{
        // Scenario 2
        await driver.get('http://localhost:8080/');

        await driver.sleep(3000);

        let connectButton = await driver.findElement(By.xpath("//button[@id='connect']"));
        await connectButton.click();

        await driver.sleep(4000);

        let scenarioButton = await driver.findElement(By.xpath("//button[@id='scenario2']"));
        await scenarioButton.click();

        await driver.sleep(2000);

        let textBox = await driver.findElement(By.id("name"));
        await textBox.click();
        await textBox.sendKeys("yes");

        let sendButton = await driver.findElement(By.xpath("//button[@id='send']"));
        await driver.sleep(4000);
        sendButton.click();

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("3");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("3");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);
        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("yes");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("3");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("yes");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("7");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("7");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("10");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("10");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("2");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("2");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        let finalStatus = await driver.findElement(By.id('main-content')).getText();
        console.log("Final Game Status:", finalStatus);

        if (finalStatus.includes("F15 F15 F20 F20 F20 F20 F25 F25 F30 H10 B15 L20") && finalStatus.includes("F10 F15 F15 F25 F30 F40 F50 L20 L20") && finalStatus.includes("F20 F40 D5 D5 S10 H10 H10 H10 H10 B15 B15 L20") && finalStatus.includes("F15 F15 F20 F25 F30 F50 F70 L20 L20")) {
            console.log("Test passed: Hands are displayed correctly.")
        } else {
            console.log("Test failed: Hands are displayed incorrectly.")
        }

        assert(finalStatus, finalStatus.includes("Player 2 has won the game!"))
        assert(finalStatus, finalStatus.includes("Player 4 has won the game!"))

        let pOneHandsize = await driver.findElement(By.xpath('//tbody/tr[1]/td[2]')).getText();
        let pTwoHandsize = await driver.findElement(By.xpath('//tbody/tr[2]/td[2]')).getText();
        let pThreeHandsize = await driver.findElement(By.xpath('//tbody/tr[3]/td[2]')).getText();
        let pFourHandsize = await driver.findElement(By.xpath('//tbody/tr[4]/td[2]')).getText();

        assert(pOneHandsize, 12)
        assert(pTwoHandsize, 9)
        assert(pThreeHandsize, 12)
        assert(pFourHandsize, 9)
    } catch (error) {
        console.error("Test encountered an error:", error);
    } finally {
        //await driver.quit();
    }
}

async function scenario3() {
    let driver = await new Builder().forBrowser('chrome').build();

    try{
        // Scenario 3
        await driver.get('http://localhost:8080/');

        await driver.sleep(3000);

        let connectButton = await driver.findElement(By.xpath("//button[@id='connect']"));
        await connectButton.click();

        await driver.sleep(4000);

        let scenarioButton = await driver.findElement(By.xpath("//button[@id='scenario3']"));
        await scenarioButton.click();

        await driver.sleep(2000);

        let textBox = await driver.findElement(By.id("name"));
        await textBox.click();
        await textBox.sendKeys("yes");

        let sendButton = await driver.findElement(By.xpath("//button[@id='send']"));
        await driver.sleep(4000);
        sendButton.click();

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("2");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("3");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);
        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("8");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("8");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("9");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("10");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("10");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("11");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("2");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("2");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("yes");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("7");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("9");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("9");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("8");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("10");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("10");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("7");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("10");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("11");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        let finalStatus = await driver.findElement(By.id('main-content')).getText();
        console.log("Final Game Status:", finalStatus);

        if (finalStatus.includes("F25 F25 F35 D5 D5 H10 H10 H10 S10 S10 S10 S10") && finalStatus.includes("F15 F25 F30 F40 H10 S10 S10 S10 E30") && finalStatus.includes("F10 F25 F30 F40 F50 H10 H10 S10 S10 L20") && finalStatus.includes("F25 F25 F30 F50 F70 D5 D5 S10 S10 B15 L20")) {
            console.log("Test passed: Hands are displayed correctly.")
        } else {
            console.log("Test failed: Hands are displayed incorrectly.")
        }

        assert(finalStatus, finalStatus.includes("Player 3 has won the game!"))

        let pOneHandsize = await driver.findElement(By.xpath('//tbody/tr[1]/td[2]')).getText();
        let pTwoHandsize = await driver.findElement(By.xpath('//tbody/tr[2]/td[2]')).getText();
        let pThreeHandsize = await driver.findElement(By.xpath('//tbody/tr[3]/td[2]')).getText();
        let pFourHandsize = await driver.findElement(By.xpath('//tbody/tr[4]/td[2]')).getText();

        assert(pOneHandsize, 12)
        assert(pTwoHandsize, 9)
        assert(pThreeHandsize, 10)
        assert(pFourHandsize, 11)

    } catch (error) {
        console.error("Test encountered an error:", error);
    } finally {
        //await driver.quit();
    }
}

async function scenario4() {
    let driver = await new Builder().forBrowser('chrome').build();

    try{
        // Scenario 4
        await driver.get('http://localhost:8080/');

        await driver.sleep(3000);

        let connectButton = await driver.findElement(By.xpath("//button[@id='connect']"));
        await connectButton.click();

        await driver.sleep(4000);

        let scenarioButton = await driver.findElement(By.xpath("//button[@id='scenario4']"));
        await scenarioButton.click();

        await driver.sleep(2000);

        let textBox = await driver.findElement(By.id("name"));
        await textBox.click();
        await textBox.sendKeys("yes");

        let sendButton = await driver.findElement(By.xpath("//button[@id='send']"));
        await driver.sleep(4000);
        sendButton.click();

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("2");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("3");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("5");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("6");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("2");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("4");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("no");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("3");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("12");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("quit");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await driver.sleep(2000);
        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        await textBox.click();
        await textBox.sendKeys("1");

        await driver.sleep(2000);
        sendButton.click();
        await driver.sleep(2000);

        let finalStatus = await driver.findElement(By.id('main-content')).getText();
        console.log("Final Game Status:", finalStatus);

        if (finalStatus.includes("F15 D5 D5 D5 D5 H10 H10 H10 H10 S10 S10 S10") && finalStatus.includes("F5 F5 F10 F15 F15 F20 F20 F25 F30 F30 F40") && finalStatus.includes("F5 F5 F10 F15 F15 F20 F20 F25 F25 F30 F40 L20") && finalStatus.includes("F5 F5 F10 F15 F15 F20 F20 F25 F25 F30 F50 E30")) {
            console.log("Test passed: Hands are displayed correctly.");
        } else {
            console.log("Test failed: Hands are displayed incorrectly.");
        }

        assert(finalStatus, finalStatus.includes("Player 3 has won the game!"))

        let pOneHandsize = await driver.findElement(By.xpath('//tbody/tr[1]/td[2]')).getText();
        let pTwoHandsize = await driver.findElement(By.xpath('//tbody/tr[2]/td[2]')).getText();
        let pThreeHandsize = await driver.findElement(By.xpath('//tbody/tr[3]/td[2]')).getText();
        let pFourHandsize = await driver.findElement(By.xpath('//tbody/tr[4]/td[2]')).getText();

        assert(pOneHandsize, 12)
        assert(pTwoHandsize, 12)
        assert(pThreeHandsize, 12)
        assert(pFourHandsize, 12)

    } catch (error) {
        console.error("Test encountered an error:", error);
    } finally {
        //await driver.quit();
    }
}