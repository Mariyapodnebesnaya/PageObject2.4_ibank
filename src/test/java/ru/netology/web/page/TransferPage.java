package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement transferPageTitle = $(byText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement fromCard = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorMessage = $("[data-test-id=error-notification] .notification__content");

    public TransferPage() {
        transferPageTitle.shouldBe(Condition.visible);
    }

    public void clickTransferButton() {
        transferButton.click();
    }

    public void makeTransfer(Integer amountToTransfer, DataHelper.CardInfo cardInfo) {
        amount.setValue(amountToTransfer.toString());
        fromCard.setValue(cardInfo.getCardNumber());
        clickTransferButton();
    }

    public DashboardPage makeValidTransfer(Integer amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldHave((Condition.exactText(expectedText)), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    public String getErrorMessageText() {
        return errorMessage.getText();
    }
}
