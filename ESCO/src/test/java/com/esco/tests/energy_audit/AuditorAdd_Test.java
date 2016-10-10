package com.esco.tests.energy_audit;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.esco.core.BaseTest;
import com.esco.core.database.DbQueries;
import com.esco.core.database.DbStatements;
import com.esco.core.web.pages.energy_audit.Auditors_FilesPage;
import com.esco.core.web.pages.energy_audit.Auditors_Page;
import com.esco.core.web.pages.energy_audit.Auditors_RegistrationPage;
import com.esco.core.web.pages.other.LogInPage;
import com.esco.core.web.pages.other.MainPage;

public class AuditorAdd_Test extends BaseTest 
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase() throws Exception
	{
	    // Определение ошибки, которая будет появляться в случае падения запроса
	    String ErrorMessage = DbQueries.DocsTests.Incoming_Docs.Deletion_Queries.DocDeletion_ErrorMessage;
	    
	    // Определение текста запроса
	    String DeletionStatement = DbQueries.DocsTests.Incoming_Docs.Deletion_Queries.DocDeletion_Statement();
	    
	    // Выполнение запроса
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	}
	
	@Test(groups = { "IncomingDocsAdd_Test" })
	public void IncomingDocsAdd_TestMethod() throws Exception
	{	
			// Переход на главную
			LogInPage authorizationPage = new LogInPage(driver).load();
			MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
			
			// Переход на страничку пользователей
			Auditors_Page docsPage = mainPage.new goTo().new DocsBlock().IncomingDocs_Page();
			docsPage.waitFor_PageReady();
			
			// Добавление карточки
			//docsPage.tree_Open();
			Auditors_RegistrationPage addPage = docsPage.card_add();
			addPage.card_Generate(sqlConnection);
			
			Auditors_RegistrationPage editPage = addPage.cardInfo_Set();
			editPage.card_Check();

			// Редактирование карточки				
			editPage.corr_ExistenceCheck();
			editPage.card_Edit();
			editPage.editedCard_Check();
			
			// Работа со вкладкой 'Связанные документы и файлы'
						
			Auditors_FilesPage filesPage = editPage.goTo_Files_Page("tricky");	
			filesPage.file_Add();
			filesPage.filesGrid_Check("add");
			filesPage.inset_Save();
			filesPage.filesGrid_Check("add");
			filesPage.file_Edit();
			filesPage.filesGrid_Check("edit");
			filesPage.inset_Save();
			filesPage.filesGrid_Check("edit");	
			filesPage.fileUnload_check();
			filesPage.file_Delete();
			
			// Проверка поиска карточки
			// Проверка редактирования
			
			docsPage = filesPage.card_Close("tricky");
			docsPage.waitFor_PageReady();
			//docsPage.tree_Open();
			docsPage.card_Search();
			docsPage.card_Check("add");
			editPage = docsPage.card_Edit();
			editPage.cardHeader_Check();
			editPage.resolution_grid_check("edit");
			editPage = editPage.shortSummary_Edit();
			docsPage = editPage.card_Close();
			
			// Проверка просмотра
			docsPage.waitFor_PageReady();
			//docsPage.tree_Open();
			docsPage.card_Search();
			docsPage.card_Check("edit");
			Auditors_RegistrationPage viewPage = docsPage.card_View();
			viewPage.cardHeader_Check();
			viewPage.resolution_grid_check("view");
			Auditors_FilesPage view_filesPage = viewPage.goTo_Files_Page("simple");
			view_filesPage.wait_ForPageReady();
			view_filesPage.filesGrid_Check("view");
			view_filesPage.fileUnload_check();
			
			// Проверка посдвечивания строки в гриде
			docsPage = view_filesPage.card_Close("simple");
			//docsPage.card_Check("edit");
			// Дописать проверку подсвечивания
			
			// Выход из системы
			docsPage.user_Out();
	}
}
