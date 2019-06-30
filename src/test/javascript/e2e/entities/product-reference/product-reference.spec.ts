/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductReferenceComponentsPage, ProductReferenceDeleteDialog, ProductReferenceUpdatePage } from './product-reference.page-object';

const expect = chai.expect;

describe('ProductReference e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productReferenceUpdatePage: ProductReferenceUpdatePage;
  let productReferenceComponentsPage: ProductReferenceComponentsPage;
  let productReferenceDeleteDialog: ProductReferenceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductReferences', async () => {
    await navBarPage.goToEntity('product-reference');
    productReferenceComponentsPage = new ProductReferenceComponentsPage();
    await browser.wait(ec.visibilityOf(productReferenceComponentsPage.title), 5000);
    expect(await productReferenceComponentsPage.getTitle()).to.eq('storeApp.productReference.home.title');
  });

  it('should load create ProductReference page', async () => {
    await productReferenceComponentsPage.clickOnCreateButton();
    productReferenceUpdatePage = new ProductReferenceUpdatePage();
    expect(await productReferenceUpdatePage.getPageTitle()).to.eq('storeApp.productReference.home.createOrEditLabel');
    await productReferenceUpdatePage.cancel();
  });

  it('should create and save ProductReferences', async () => {
    const nbButtonsBeforeCreate = await productReferenceComponentsPage.countDeleteButtons();

    await productReferenceComponentsPage.clickOnCreateButton();
    await promise.all([
      productReferenceUpdatePage.setNameInput('name'),
      productReferenceUpdatePage.setRefNameInput('refName'),
      productReferenceUpdatePage.setReferenceInput('reference'),
      productReferenceUpdatePage.setTypeInput('type'),
      productReferenceUpdatePage.productSelectLastOption()
    ]);
    expect(await productReferenceUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productReferenceUpdatePage.getRefNameInput()).to.eq('refName', 'Expected RefName value to be equals to refName');
    expect(await productReferenceUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');
    expect(await productReferenceUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');
    await productReferenceUpdatePage.save();
    expect(await productReferenceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productReferenceComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductReference', async () => {
    const nbButtonsBeforeDelete = await productReferenceComponentsPage.countDeleteButtons();
    await productReferenceComponentsPage.clickOnLastDeleteButton();

    productReferenceDeleteDialog = new ProductReferenceDeleteDialog();
    expect(await productReferenceDeleteDialog.getDialogTitle()).to.eq('storeApp.productReference.delete.question');
    await productReferenceDeleteDialog.clickOnConfirmButton();

    expect(await productReferenceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
