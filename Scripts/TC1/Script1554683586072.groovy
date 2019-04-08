import com.kms.katalon.core.util.KeywordUtil

//schema file
def schema = new File('./Include/resources/languageGroupsSchema.json').text
KeywordUtil.logInfo("Expected Schema: " + schema)

//send request
//def group_response = WS.sendRequest(findTestObject('Object Repository/API/TransAPI/translations_groups'))

//validate schema
def responseBodyContent = '''
[
    {
        "ID": 1,
        "Name": "Warning"
    },
    {
        "ID": 2,
        "Name": “TextArea”
    },
    {
        "ID": 3,
        "Name": "LastActions"
    },
    {
        "ID": 4,
        "Name": "DayOfWeek"
    },
    {
        "ID": 5,
        "Name": "Icons"
    },
    {
        "ID": 6,
        "Name": "Direction"
    },
    {
        "ID": 7,
        "Name": "Tendency"
    },
    {
        "ID": 8,
        "Name": "AirTypes"
    },
    {
        "ID": 9,
        "Name": "UCCText"
    },
    {
        "ID": 10,
        "Name": "Statements"
    }
]
'''
//def responseJson = group_response.getResponseBodyContent().replace("[", "").replace("]", "")
//def responseJson = responseBodyContent.replace("[", "").replace("]", "")
def responseJson = responseBodyContent
CustomKeywords.'com.ws.EveritValidator.verifyJsonSchema'(responseJson, schema)