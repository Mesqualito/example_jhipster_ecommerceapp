import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class CustomerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-customer div table .btn-danger'));
  title = element.all(by.css('jhi-customer div h2#page-heading span')).first();

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

export class CustomerUpdatePage {
  pageTitle = element(by.id('jhi-customer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  erpIdInput = element(by.id('field_erpId'));
  name1Input = element(by.id('field_name1'));
  name2Input = element(by.id('field_name2'));
  name3Input = element(by.id('field_name3'));
  emailInput = element(by.id('field_email'));
  phoneInput = element(by.id('field_phone'));
  addressLine1Input = element(by.id('field_addressLine1'));
  addressLine2Input = element(by.id('field_addressLine2'));
  addressLine3Input = element(by.id('field_addressLine3'));
  plzInput = element(by.id('field_plz'));
  cityInput = element(by.id('field_city'));
  countryInput = element(by.id('field_country'));
  userSelect = element(by.id('field_user'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setErpIdInput(erpId) {
    await this.erpIdInput.sendKeys(erpId);
  }

  async getErpIdInput() {
    return await this.erpIdInput.getAttribute('value');
  }

  async setName1Input(name1) {
    await this.name1Input.sendKeys(name1);
  }

  async getName1Input() {
    return await this.name1Input.getAttribute('value');
  }

  async setName2Input(name2) {
    await this.name2Input.sendKeys(name2);
  }

  async getName2Input() {
    return await this.name2Input.getAttribute('value');
  }

  async setName3Input(name3) {
    await this.name3Input.sendKeys(name3);
  }

  async getName3Input() {
    return await this.name3Input.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return await this.emailInput.getAttribute('value');
  }

  async setPhoneInput(phone) {
    await this.phoneInput.sendKeys(phone);
  }

  async getPhoneInput() {
    return await this.phoneInput.getAttribute('value');
  }

  async setAddressLine1Input(addressLine1) {
    await this.addressLine1Input.sendKeys(addressLine1);
  }

  async getAddressLine1Input() {
    return await this.addressLine1Input.getAttribute('value');
  }

  async setAddressLine2Input(addressLine2) {
    await this.addressLine2Input.sendKeys(addressLine2);
  }

  async getAddressLine2Input() {
    return await this.addressLine2Input.getAttribute('value');
  }

  async setAddressLine3Input(addressLine3) {
    await this.addressLine3Input.sendKeys(addressLine3);
  }

  async getAddressLine3Input() {
    return await this.addressLine3Input.getAttribute('value');
  }

  async setPlzInput(plz) {
    await this.plzInput.sendKeys(plz);
  }

  async getPlzInput() {
    return await this.plzInput.getAttribute('value');
  }

  async setCityInput(city) {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput() {
    return await this.cityInput.getAttribute('value');
  }

  async setCountryInput(country) {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput() {
    return await this.countryInput.getAttribute('value');
  }

  async userSelectLastOption(timeout?: number) {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return await this.userSelect.element(by.css('option:checked')).getText();
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

export class CustomerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-customer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-customer'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
