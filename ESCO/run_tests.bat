set env=dev
set brw=chrome
set log=C:\Selenium_TestData\Projects\ESCO\Other\BatLog\%brw%_%env%_run_%date:/=-%.log
echo start > %log%
echo ================ >> %log%

:: ������ ������
mvn clean test -D browser=%brw% -D environment=%env% >> %log%

echo ================ >> %log%
echo done >> %log%