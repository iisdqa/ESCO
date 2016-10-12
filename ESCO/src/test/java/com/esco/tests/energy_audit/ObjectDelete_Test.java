package com.esco.tests.energy_audit;

import org.testng.annotations.Test;

import com.esco.core.BaseTest;
import com.esco.core.web.pages.energy_audit.objects.Objects_Page;
import com.esco.core.web.pages.other.LogInPage;
import com.esco.core.web.pages.other.MainPage;

public class ObjectDelete_Test extends BaseTest 
{
	@Test(groups = { "ObjectDelete_Test" })
	public void IncomingDocsAdd_TestMethod() throws Exception
	{	
			// ������� �� �������
			LogInPage authorizationPage = new LogInPage(driver).load();
			MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
			
			// ������� �� ��������� �������������
			Objects_Page objectsPage = mainPage.new goTo().new EnergyAudit_Block().Objects_Page();
			objectsPage.waitFor_PageReady();

			// ����� ��������
			objectsPage.card_Search();
			objectsPage.card_Check("edit");

			// �������� ��������
			objectsPage.card_Delete();
			objectsPage.cardDeletion_Check();
			
			// ����� �� �������
			objectsPage.user_Out();
	}
}
