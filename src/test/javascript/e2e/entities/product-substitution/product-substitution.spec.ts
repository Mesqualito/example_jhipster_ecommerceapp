/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ProductSubstitutionComponentsPage,
  ProductSubstitutionDeleteDialog,
  ProductSubstitutionUpdatePage
} from './product-substitution.page-object';

const expect = chai.expect;

describe('ProductSubstitution e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productSubstitutionUpdatePage: ProductSubstitutionUpdatePage;
  let productSubstitutionComponentsPage: ProductSubstitutionComponentsPage;
  let productSubstitutionDeleteDialog: ProductSubstitutionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductSubstitutions', async () => {
    await navBarPage.goToEntity('product-substitution');
    productSubstitutionComponentsPage = new ProductSubstitutionComponentsPage();
    await browser.wait(ec.visibilityOf(productSubstitutionComponentsPage.title), 5000);
    expect(await productSubstitutionComponentsPage.getTitle()).to.eq('storeApp.productSubstitution.home.title');
  });

  it('should load create ProductSubstitution page', async () => {
    await productSubstitutionComponentsPage.clickOnCreateButton();
    productSubstitutionUpdatePage = new ProductSubstitutionUpdatePage();
    expect(await productSubstitutionUpdatePage.getPageTitle()).to.eq('storeApp.productSubstitution.home.createOrEditLabel');
    await productSubstitutionUpdatePage.cancel();
  });

  it('should create and save ProductSubstitutions', async () => {
    const nbButtonsBeforeCreate = await productSubstitutionComponentsPage.countDeleteButtons();

    await productSubstitutionComponentsPage.clickOnCreateButton();
    await promise.all([productSubstitutionUpdatePage.setNameInput('name')]);
    expect(await productSubstitutionUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    const selectedExchangeable = productSubstitutionUpdatePage.getExchangeableInput();
    if (await selectedExchangeable.isSelected()) {
      await productSubstitutionUpdatePage.getExchangeableInput().click();
      expect(await productSubstitutionUpdatePage.getExchangeableInput().isSelected(), 'Expected exchangeable not to be selected').to.be
        .false;
    } else {
      await productSubstitutionUpdatePage.getExchangeableInput().click();
      expect(await productSubstitutionUpdatePage.getExchangeableInput().isSelected(), 'Expected exchangeable to be selected').to.be.true;
    }
    const selectedChecked = productSubstitutionUpdatePage.getCheckedInput();
    if (await selectedChecked.isSelected()) {
      await productSubstitutionUpdatePage.getCheckedInput().click();
      expect(await productSubstitutionUpdatePage.getCheckedInput().isSelected(), 'Expected checked not to be selected').to.be.false;
    } else {
      await productSubstitutionUpdatePage.getCheckedInput().click();
      expect(await productSubstitutionUpdatePage.getCheckedInput().isSelected(), 'Expected checked to be selected').to.be.true;
    }
    await productSubstitutionUpdatePage.save();
    expect(await productSubstitutionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productSubstitutionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductSubstitution', async () => {
    const nbButtonsBeforeDelete = await productSubstitutionComponentsPage.countDeleteButtons();
    await productSubstitutionComponentsPage.clickOnLastDeleteButton();

    productSubstitutionDeleteDialog = new ProductSubstitutionDeleteDialog();
    expect(await productSubstitutionDeleteDialog.getDialogTitle()).to.eq('storeApp.productSubstitution.delete.question');
    await productSubstitutionDeleteDialog.clickOnConfirmButton();

    expect(await productSubstitutionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
