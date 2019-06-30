import { by, element, ElementFinder } from 'protractor';

export class ProductSubstitutionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-substitution div table .btn-danger'));
  title = element.all(by.css('jhi-product-substitution div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ProductSubstitutionUpdatePage {
  pageTitle = element(by.id('jhi-product-substitution-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  exchangeableInput = element(by.id('field_exchangeable'));
  checkedInput = element(by.id('field_checked'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  getExchangeableInput(timeout?: number) {
    return this.exchangeableInput;
  }
  getCheckedInput(timeout?: number) {
    return this.checkedInput;
  }
  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProductSubstitutionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productSubstitution-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productSubstitution'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
