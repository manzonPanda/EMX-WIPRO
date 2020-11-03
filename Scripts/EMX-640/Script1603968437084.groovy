import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

'Open Browser'
WebUI.openBrowser('')

'Navigate to URL'
WebUI.navigateToUrl(GlobalVariable.StageUrl)

'Maximize Window'
WebUI.maximizeWindow()

'Delay for 5 seconds'
WebUI.delay(5)

'Initialize driver'
WebDriver driver = DriverFactory.getWebDriver()

'Set a username'
WebUI.setText(findTestObject('tiLoginUsername'), GlobalVariable.username)
WebUI.delay(3)
'Set a password'
WebUI.setText(findTestObject('tiLoginPassword'), GlobalVariable.password)
'Click signin'
WebUI.click(findTestObject("tiLoginButton"))

WebUI.delay(10)

'Verify Support link in the upper right pointing to ITSM'
try{
	'Delay for 3 seconds'
	WebUI.delay(3)
	WebUI.verifyElementPresent(findTestObject('SupportLink'), 30)
	KeywordUtil.markPassed("VERIFIED: Support link in the upper right pointing to ITSM found.")
		
}catch(Exception e){
	KeywordUtil.markFailedAndStop("FAILED: Support link in the upper right pointing to ITSM does not found.")
}	

'Verify Updated text when confirming ticket creation'
try{
	WebElement onDemandButton = driver.findElement(By.xpath('//a[text()="On-Demand"]'))
	onDemandButton.click()
	WebUI.delay(3)
	WebElement dataUploadFormButton = driver.findElement(By.xpath('//a[@id="onDemandForm"]'))
	dataUploadFormButton.click()
	WebUI.delay(5)
	'Select Facility'
	WebUI.selectOptionByLabel(findTestObject('facilitySelectElement'),"CLARK-PR", false)
	WebUI.delay(3)
	
	'Choose Parametric(WSP)'
	WebElement parametricWSP = driver.findElement(By.xpath('//input[@name="WSP"]'))
	parametricWSP.click()
	
	'Choose Extraction Modes'
	WebElement mergedMode = driver.findElement(By.xpath('//input[@name="MERGED"]'))
	mergedMode.click()
	
	'Choose fromDate'
	WebElement fromDate = driver.findElement(By.xpath('//input[@name="fromDate"]'))
	fromDate.clear()
	fromDate.sendKeys("10/27/2020")
	WebUI.delay(3)
	'Choose toDate'
	WebElement toDate = driver.findElement(By.xpath('//input[@name="toDate"]'))
	toDate.clear()
	toDate.sendKeys("10/28/2020")
	WebUI.delay(3)
	'Click submit button'
	WebElement submitButton = driver.findElement(By.xpath('//input[@id="submitOnDemandForm"]'))
	submitButton.click()
	
	WebUI.delay(30)
	
	'Click the first record'
	WebElement firstRecord = driver.findElement(By.xpath('//table[@id="onDemandDataTbl"]/thead/tr/th[3]'))
	Actions action = new Actions(driver);
	action.moveToElement(firstRecord).build().perform();
	WebUI.delay(3)
	action.moveByOffset(0,3).perform()
	action.click().perform()
	WebUI.delay(3)
	WebElement proceedButton = driver.findElement(By.xpath('//span[text()="Proceed To Extract"]'))
	proceedButton.click()
	
	WebUI.waitForElementPresent(findTestObject("proceedToExtractDialog"), 30)
	WebElement raiseTicket = driver.findElement(By.xpath('//input[@id="raiseTicket"]/parent::p'))
	(raiseTicket.getAttribute("innerHTML").contains("Raise a ticket")) ? KeywordUtil.markPassed("PASSED: Ticket creation found."):KeywordUtil.markFailedAndStop("FAILED: Ticket creation not found.")
	
}catch(Exception e){
	KeywordUtil.markFailedAndStop("FAILED: Ticket creation not found.")
}


'Verify Ticket submission'
try{
	'Navigate to URL'
	WebUI.navigateToUrl(GlobalVariable.cherwellUrl)

	WebUI.delay(5)
	WebUI.waitForElementPresent(findTestObject("cherwellLoginForm"), 60)

	'Set a username'
	WebUI.setText(findTestObject('cherwellUsername'), GlobalVariable.username)
	WebUI.delay(3)
	'Set a password'
	WebUI.setText(findTestObject('cherwellPassword'), GlobalVariable.password)
	'Click signin'
	WebUI.click(findTestObject("cherwellSignin"))
	
	WebUI.waitForElementPresent(findTestObject("cherwellLogo"), 60)
	WebElement searchesButton = driver.findElement(By.xpath('//span[text()="Searches"]'))
	searchesButton.click()
	WebUI.delay(3)
	WebElement allIncidents = driver.findElement(By.xpath('//span[text()="All Incidents"]'))
	allIncidents.click()
	WebUI.delay(3)
	
	WebUI.verifyElementPresent(findTestObject("ticketCreation"), 30)
	KeywordUtil.markPassed("PASSED: Ticket creation found.")
	
}catch(Exception e){
	KeywordUtil.markFailedAndStop("FAILED: Ticket creation not found.")
}