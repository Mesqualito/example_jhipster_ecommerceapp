/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ShopImageComponentsPage, ShopImageDeleteDialog, ShopImageUpdatePage } from './shop-image.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('ShopImage e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shopImageUpdatePage: ShopImageUpdatePage;
  let shopImageComponentsPage: ShopImageComponentsPage;
  let shopImageDeleteDialog: ShopImageDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ShopImages', async () => {
    await navBarPage.goToEntity('shop-image');
    shopImageComponentsPage = new ShopImageComponentsPage();
    await browser.wait(ec.visibilityOf(shopImageComponentsPage.title), 5000);
    expect(await shopImageComponentsPage.getTitle()).to.eq('storeApp.shopImage.home.title');
  });

  it('should load create ShopImage page', async () => {
    await shopImageComponentsPage.clickOnCreateButton();
    shopImageUpdatePage = new ShopImageUpdatePage();
    expect(await shopImageUpdatePage.getPageTitle()).to.eq('storeApp.shopImage.home.createOrEditLabel');
    await shopImageUpdatePage.cancel();
  });

  it('should create and save ShopImages', async () => {
    const nbButtonsBeforeCreate = await shopImageComponentsPage.countDeleteButtons();

    await shopImageComponentsPage.clickOnCreateButton();
    await promise.all([
      shopImageUpdatePage.setNameInput('name'),
      shopImageUpdatePage.setHerstArtNrInput('herstArtNr'),
      shopImageUpdatePage.setOrderInput('5'),
      shopImageUpdatePage.sizeSelectLastOption(),
      shopImageUpdatePage.setDescriptionInput('description'),
      shopImageUpdatePage.setImageInput(absolutePath),
      shopImageUpdatePage.productSelectLastOption()
    ]);
    expect(await shopImageUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await shopImageUpdatePage.getHerstArtNrInput()).to.eq('herstArtNr', 'Expected HerstArtNr value to be equals to herstArtNr');
    expect(await shopImageUpdatePage.getOrderInput()).to.eq('5', 'Expected order value to be equals to 5');
    expect(await shopImageUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await shopImageUpdatePage.getImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected Image value to be end with ' + fileNameToUpload
    );
    await shopImageUpdatePage.save();
    expect(await shopImageUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await shopImageComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ShopImage', async () => {
    const nbButtonsBeforeDelete = await shopImageComponentsPage.countDeleteButtons();
    await shopImageComponentsPage.clickOnLastDeleteButton();

    shopImageDeleteDialog = new ShopImageDeleteDialog();
    expect(await shopImageDeleteDialog.getDialogTitle()).to.eq('storeApp.shopImage.delete.question');
    await shopImageDeleteDialog.clickOnConfirmButton();

    expect(await shopImageComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
