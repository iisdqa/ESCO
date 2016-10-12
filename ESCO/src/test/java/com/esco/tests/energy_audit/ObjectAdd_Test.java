package com.esco.tests.energy_audit;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.esco.core.BaseTest;
import com.esco.core.database.DbQueries;
import com.esco.core.database.DbStatements;
import com.esco.core.web.pages.energy_audit.objects.*;
import com.esco.core.web.pages.other.LogInPage;
import com.esco.core.web.pages.other.MainPage;

public class ObjectAdd_Test extends BaseTest 
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
	
	@Test(groups = { "ObjectAdd_Test" })
	public void ObjectAdd_TestMethod() throws Exception
	{	
			// ������� �� �������
			LogInPage authorizationPage = new LogInPage(driver).load();
			MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
			
			// ������� �� ��������� �������������
			Objects_Page objectsPage = mainPage.new goTo().new EnergyAudit_Block().Objects_Page();
			objectsPage.waitFor_PageReady();
			
			// ���������� ��������
			Objects_RegistrationPage addPage = objectsPage.card_add();
			Objects_RegistrationPage editPage = addPage.cardInfo_Set();
			editPage.card_Check();
			editPage.koatuuEdit_Check();
			editPage.uniqueModel_Check();
			
			// ������ � �������� '������ ��������'
			
			Objects_PropertyAttributesPage attributesInset = editPage.goTo_PropertyAttributes_Page("tricky");	
			attributesInset.attributes_Set();
			attributesInset.attributes_Check();
			attributesInset.inset_Edit();
			attributesInset.editedInset_Check();
			attributesInset.ownerName_Change();
			
			// ������ � �������� '���������� ���������
			
			Objects_CountersPage countersInset = attributesInset.goTo_Counter_Page("tricky");
			countersInset.info_Set();
			countersInset.counter_Add();
			countersInset.counter_Check("add");
			countersInset.inset_Save();
			countersInset.info_Check();
			countersInset.counter_Check("add");
			countersInset.counter_Edit();
			countersInset.counter_Check("edit");
			countersInset.inset_Save();
			countersInset.counter_Check("edit");
			countersInset.inset_Edit();
			countersInset.editedInset_Check();
			countersInset.comment_Change();
			objectsPage = countersInset.card_Close("tricky");
			
/*			// ������ �� �������� '��������� ��������� � �����'
						
			Objects_FilesPage filesInset = countersInset.goTo_Files_Page("tricky");
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
			auditorsPage = filesInset.card_Close("tricky");*/
			
			// �������� ������ ��������
			// �������� ��������������
			

			objectsPage.waitFor_PageReady();
			objectsPage.card_Search();
			objectsPage.card_Check("add");
			editPage = objectsPage.card_Edit();
			editPage.cardHeader_Check();
			editPage.card_Edit();
			editPage.editedCard_Check();
			objectsPage = editPage.card_Close();
			
			// �������� ���������('������� ��������')
			objectsPage.waitFor_PageReady();
			objectsPage.card_Search();
			objectsPage.card_Check("edit");
			Objects_RegistrationPage viewPage = objectsPage.card_View();
			viewPage.cardHeader_Check();
			viewPage.viewCard_Check();
			
			// �������� ���������('������ ��������')
			Objects_PropertyAttributesPage attributesView_Inset = viewPage.goTo_PropertyAttributes_Page("simple");
			attributesView_Inset.attributes_Check();
			
			// �������� ���������('������ ��������')
			Objects_CountersPage countersViewInset = attributesView_Inset.goTo_Counter_Page("simple");
			countersViewInset.infoView_Check();
			countersViewInset.counter_Check("view");
			
/*			// �������� ���������('��'���� ��������� �� �����')
			Objects_FilesPage view_filesInset = countersViewInset.goTo_Files_Page("simple");
			view_filesInset.wait_ForPageReady();
			view_filesInset.filesGrid_Check("view");
			view_filesInset.fileUnload_check();*/
			
			// �������� ������������� ������ � �����
			objectsPage = countersViewInset.card_Close("simple");
			objectsPage.waitFor_PageReady();
			objectsPage.card_Check("edit");
			// �������� �������� �������������
			
			// ����� �� �������
			objectsPage.user_Out();
	}
}
