package com.esco.tests.energy_audit;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.esco.core.BaseTest;
import com.esco.core.database.DbQueries;
import com.esco.core.database.DbStatements;
import com.esco.core.web.pages.energy_audit.auditors.Auditors_FilesPage;
import com.esco.core.web.pages.energy_audit.auditors.Auditors_Page;
import com.esco.core.web.pages.energy_audit.auditors.Auditors_RegistrationPage;
import com.esco.core.web.pages.other.LogInPage;
import com.esco.core.web.pages.other.MainPage;

public class AuditorAdd_Test extends BaseTest 
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase() throws Exception
	{
	    // ����������� ������, ������� ����� ���������� � ������ ������� �������
	    String ErrorMessage = DbQueries.EnergyAuditTests.Auditors.Deletion_Queries.auditorDeletion_ErrorMessage;
	    
	    // ����������� ������ �������
	    String DeletionStatement = DbQueries.EnergyAuditTests.Auditors.Deletion_Queries.auditorDeletion_Statement;
	    
	    // ���������� �������
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	}
	
	@Test(groups = { "AuditorAdd_Test" })
	public void IncomingDocsAdd_TestMethod() throws Exception
	{	
			// ������� �� �������
			LogInPage authorizationPage = new LogInPage(driver).load();
			MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
			
			// ������� �� ��������� �������������
			Auditors_Page auditorsPage = mainPage.new goTo().new EnergyAudit_Block().Auditors_Page();
			auditorsPage.waitFor_PageReady();
			
			// ���������� ��������
			Auditors_RegistrationPage addPage = auditorsPage.card_add();
			Auditors_RegistrationPage editPage = addPage.cardInfo_Set();
			editPage.card_Check();
			editPage.email_Edit();
			
			// ������ �� �������� '��������� ��������� � �����'
						
			Auditors_FilesPage filesInset = editPage.goTo_Files_Page("tricky");	
			filesInset.file_Add();
			filesInset.filesGrid_Check("add");
			filesInset.inset_Save();
			filesInset.filesGrid_Check("add");
			filesInset.file_Edit();
			filesInset.filesGrid_Check("edit");
			filesInset.inset_Save();
			filesInset.filesGrid_Check("edit");	
			filesInset.fileUnload_check();
			filesInset.file_Delete();
			
			// �������� ������ ��������
			// �������� ��������������
			
			auditorsPage = filesInset.card_Close("tricky");
			auditorsPage.waitFor_PageReady();
			auditorsPage.card_Search();
			auditorsPage.card_Check("add");
			editPage = auditorsPage.card_Edit();
			editPage.cardHeader_Check();
			editPage.card_Edit();
			editPage.editedCard_Check();
			auditorsPage = editPage.card_Close();
			
			// �������� ���������
			auditorsPage.waitFor_PageReady();
			auditorsPage.card_Search();
			auditorsPage.card_Check("edit");
			Auditors_RegistrationPage viewPage = auditorsPage.card_View();
			viewPage.cardHeader_Check();
			Auditors_FilesPage view_filesInset = viewPage.goTo_Files_Page("simple");
			view_filesInset.wait_ForPageReady();
			view_filesInset.filesGrid_Check("view");
			view_filesInset.fileUnload_check();
			
			// �������� ������������� ������ � �����
			auditorsPage = view_filesInset.card_Close("simple");
			auditorsPage.waitFor_PageReady();
			auditorsPage.card_Check("edit");
			// �������� �������� �������������
			
			// ����� �� �������
			auditorsPage.user_Out();
	}
}
