How to validate the JSON response with expected JSON schema
==========

by kazurayam at 2019/April/08

This is a Katalon Studio project for demonstration purpose.
You can clone out this and use it on your PC.

This project was created using Katalon Studio 6.1.1

This project was created in order to reproduce a problem raised
in the Katalon Forum: [How to validate the JSON response with expected JSON schema](https://forum.katalon.com/t/how-to-validate-the-json-response-with-expected-json-schema/14029/7)

This project assumes you have
1. Katalon Studio installed on your PC
2. [Gradle](https://gradle.org/) is installed on your PC

In order to downloaded necessary jars in the `<projectDir>/Drivers`, execute the following command in the command line:

```
$> cd <projectDir>
#> gradle -b build.gradle katalonCopyDependencies
```

You want to execute `Test Cases/TC1` to run the demo.

The Keyword `Keywords/com/ws/EveriyValidator` was originally coded the questioner. When I execute the original code, I got the following error:
```
2019-04-08 10:26:41.264 ERROR c.k.katalon.core.main.TestCaseExecutor   - ❌ com.ws.EveritValidator.verifyJsonSchema(responseJson, schema) FAILED.
Reason:
org.json.JSONException: A JSONObject text must begin with '{' at 2 [character 1 line 2]
	at org.json.JSONTokener.syntaxError(JSONTokener.java:433)
	at org.json.JSONObject.<init>(JSONObject.java:194)
	at org.json.JSONObject.<init>(JSONObject.java:321)
	at com.ws.EveritValidator.verifyJsonSchema(EveritValidator.groovy:25)
	at com.ws.EveritValidator.invokeMethod(EveritValidator.groovy)
	at com.kms.katalon.core.main.CustomKeywordDelegatingMetaClass.invokeStaticMethod(CustomKeywordDelegatingMetaClass.java:49)
	at TC1.run(TC1:58)
	at com.kms.katalon.core.main.ScriptEngine.run(ScriptEngine.java:194)
	at com.kms.katalon.core.main.ScriptEngine.runScriptAsRawText(ScriptEngine.java:119)
	at com.kms.katalon.core.main.TestCaseExecutor.runScript(TestCaseExecutor.java:342)
	at com.kms.katalon.core.main.TestCaseExecutor.doExecute(TestCaseExecutor.java:333)
	at com.kms.katalon.core.main.TestCaseExecutor.processExecutionPhase(TestCaseExecutor.java:312)
	at com.kms.katalon.core.main.TestCaseExecutor.accessMainPhase(TestCaseExecutor.java:304)
	at com.kms.katalon.core.main.TestCaseExecutor.execute(TestCaseExecutor.java:238)
	at com.kms.katalon.core.main.TestCaseMain.runTestCase(TestCaseMain.java:114)
	at com.kms.katalon.core.main.TestCaseMain.runTestCase(TestCaseMain.java:105)
	at com.kms.katalon.core.main.TestCaseMain$runTestCase$0.call(Unknown Source)
	at TempTestCase1554686798644.run(TempTestCase1554686798644.groovy:21)

```

I made a short search and found a informative post in the StackOverflow.
- https://stackoverflow.com/questions/19399135/a-jsonobject-text-must-begin-with-error

Then I made a slight change to the keyword.

OLD
```
class EveritValidator {
    @Keyword
    def verifyJsonSchema(String stringJson, String stringSchema) {
        JSONObject rawSchema = new JSONObject(new JSONTokener(stringSchema))
        Schema schema = SchemaLoader.load(rawSchema)
        try {		
            schema.validate(new JSONObject(stringJson))
            //schema.validate(new JSONArray(stringJson))
```

NEW
```
class EveritValidator {
    @Keyword
    def verifyJsonSchema(String stringJson, String stringSchema) {
        JSONObject rawSchema = new JSONObject(new JSONTokener(stringSchema))
        Schema schema = SchemaLoader.load(rawSchema)
        try {		
            //schema.validate(new JSONObject(stringJson))
            schema.validate(new JSONArray(stringJson))

```

Then the problem was fixed.
