package com.esco.tests.energy_audit;

import org.testng.annotations.Test;

import com.esco.core.BaseTest;
import com.esco.core.web.pages.energy_audit.Auditors_Page;
import com.esco.core.web.pages.other.LogInPage;
import com.esco.core.web.pages.other.MainPage;

public class AuditorDelete_Test extends BaseTest 
{
	@Test(groups = { "AuditorDelete_Test" })
	public void IncomingDocsAdd_TestMethod() throws Exception
	{	
			// Переход на главную
			LogInPage authorizationPage = new LogInPage(driver).load();
			MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
			
			// Переход на страничку пользователей
			Auditors_Page auditorsPage = mainPage.new goTo().new EnergyAudit_Block().Auditors_Page();
			auditorsPage.waitFor_PageReady();

			// Поиск карточки
			auditorsPage.card_Search();
			auditorsPage.card_Check("edit");

			// Удаление карточки
			auditorsPage.card_Delete();
			auditorsPage.cardDeletion_Check();
			
			// Выход из системы
			auditorsPage.user_Out();
	}
}
