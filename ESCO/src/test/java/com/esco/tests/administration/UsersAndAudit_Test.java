package com.esco.tests.administration;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.esco.core.BaseTest;
import com.esco.core.database.DbQueries;
import com.esco.core.database.DbStatements;
import com.esco.core.web.pages.administration.AuditPage;
import com.esco.core.web.pages.administration.UserAddPage;
import com.esco.core.web.pages.administration.UserEditPage;
import com.esco.core.web.pages.administration.UserViewPage;
import com.esco.core.web.pages.administration.UsersPage;
import com.esco.core.web.pages.other.LogInPage;
import com.esco.core.web.pages.other.MainPage;

public class UsersAndAudit_Test extends BaseTest 
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase() throws Exception
	{
	    // Определение ошибки, которая будет появляться в случае падения запроса
	    String ErrorMessage = DbQueries.AdministrationTests.UsersAndAuditQueries.DeletionQueries.ErrorMessage;
	    
	    // Определение текста запроса
	    String DeletionStatement = DbQueries.AdministrationTests.UsersAndAuditQueries.DeletionQueries.UserDeletionStatement;
	    
	    // Выполнение запроса
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	}
	
	@Test(groups = { "UsersAndAudit_Test" })
	public void UsersAndAudit_TestMethod()
	{	
			// Переход на главную
			LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
			MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
			
			// Переход на страничку пользователей
			UsersPage usersPage = mainPage.new goTo().new AdministrationBlock().Users_Page();
			usersPage.WaitForGridReady();
			
			// Добавление пользователя
			UserAddPage addPage = usersPage.AddButtonClick();
			addPage.OptionalFieldsFill();
			addPage.ValidatorsCheck();
			addPage.RequiredFieldsFill();
			usersPage = addPage.UserSave();
			usersPage.WaitForGridReady();
			
			// Проверка добавленного пользователя
			// Редактирование пользователя
			// Просмотр пользователя
		
			usersPage.UserSearch();
			usersPage.AddedUserCheck();
			UserEditPage editPage = usersPage.EdidButtonClick();
			editPage.UserPositionChange();
			usersPage = editPage.UserUpdate();
			usersPage.WaitForGridReady();
			usersPage.UserSearch();
			usersPage.EditedUserCheck();
			UserViewPage viewPage = usersPage.ViewButtonClick();
			viewPage.ViewPageCheck();
			authorizationPage = viewPage.userOut();
			
			// Вход под созданным юзером
			
			mainPage = authorizationPage.logInAs("auto_user", "auto_user");
			mainPage.userNameCheck("Тестовко Василий Петрович");
			authorizationPage = mainPage.user_Out();
			
			// Проверка аудита
			
			mainPage = authorizationPage.logInAs("admin_auto", "123456");
			AuditPage auditPage = mainPage.new goTo().new AdministrationBlock().Audit_Page();
			auditPage.operationsSearch();
			auditPage.operationsCheck();
			
			// Удаление пользователя
			
			mainPage = auditPage.BackTo_MainPage();
			usersPage = mainPage.new goTo().new AdministrationBlock().Users_Page();
			usersPage.WaitForGridReady();
			usersPage.UserSearch();
			usersPage.EditedUserCheck();
			usersPage.UserDelete();
			usersPage.UserDeletionCheck();
			authorizationPage = usersPage.userOut();
	}
}
