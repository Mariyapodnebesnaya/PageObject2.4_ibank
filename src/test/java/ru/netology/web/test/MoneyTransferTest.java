package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.generateValidAmount;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFirstCards() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        int firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.makeTransfer(amount, secondCardInfo);
        Assertions.assertEquals(expectedBalanceFirstCard, dashboardPage.getCardBalance(firstCardInfo));
        Assertions.assertEquals(expectedBalanceSecondCard, dashboardPage.getCardBalance(secondCardInfo));
    }

    @Test
    void shouldTransferMoneySecondCards() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        int firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(secondCardBalance);
        var expectedBalanceFirstCard = secondCardBalance + amount;
        var expectedBalanceSecondCard = firstCardBalance - amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        transferPage.makeTransfer(amount, firstCardInfo);
        Assertions.assertEquals(expectedBalanceFirstCard, dashboardPage.getCardBalance(secondCardInfo));
        Assertions.assertEquals(expectedBalanceSecondCard, dashboardPage.getCardBalance(firstCardInfo));
    }

    @Test
    void emptyDashboardFormSubmit() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.clickTransferButton();
        Assertions.assertEquals("Ошибка! Произошла ошибка", transferPage.getErrorMessageText());
    }
}
