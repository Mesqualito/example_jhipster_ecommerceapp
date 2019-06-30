import { by, element, ElementFinder } from 'protractor';

export class ShopImageComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-shop-image div table .btn-danger'));
  title = element.all(by.css('jhi-shop-image div h2#page-heading span')).first();

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

export class ShopImageUpdatePage {
  pageTitle = element(by.id('jhi-shop-image-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  herstArtNrInput = element(by.id('field_herstArtNr'));
  orderInput = element(by.id('field_order'));
  sizeSelect = element(by.id('field_size'));
  descriptionInput = element(by.id('field_description'));
  imageInput = element(by.id('file_image'));
  productSelect = element(by.id('field_product'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setHerstArtNrInput(herstArtNr) {
    await this.herstArtNrInput.sendKeys(herstArtNr);
  }

  async getHerstArtNrInput() {
    return await this.herstArtNrInput.getAttribute('value');
  }

  async setOrderInput(order) {
    await this.orderInput.sendKeys(order);
  }

  async getOrderInput() {
    return await this.orderInput.getAttribute('value');
  }

  async setSizeSelect(size) {
    await this.sizeSelect.sendKeys(size);
  }

  async getSizeSelect() {
    return await this.sizeSelect.element(by.css('option:checked')).getText();
  }

  async sizeSelectLastOption(timeout?: number) {
    await this.sizeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setImageInput(image) {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput() {
    return await this.imageInput.getAttribute('value');
  }

  async productSelectLastOption(timeout?: number) {
    await this.productSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productSelectOption(option) {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption() {
    return await this.productSelect.element(by.css('option:checked')).getText();
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

export class ShopImageDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-shopImage-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-shopImage'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
