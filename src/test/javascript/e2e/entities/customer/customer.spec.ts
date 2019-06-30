/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CustomerComponentsPage, CustomerUpdatePage } from './customer.page-object';

const expect = chai.expect;

describe('Customer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let customerUpdatePage: CustomerUpdatePage;
  let customerComponentsPage: CustomerComponentsPage;
  /*let customerDeleteDialog: CustomerDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Customers', async () => {
    await navBarPage.goToEntity('customer');
    customerComponentsPage = new CustomerComponentsPage();
    await browser.wait(ec.visibilityOf(customerComponentsPage.title), 5000);
    expect(await customerComponentsPage.getTitle()).to.eq('storeApp.customer.home.title');
  });

  it('should load create Customer page', async () => {
    await customerComponentsPage.clickOnCreateButton();
    customerUpdatePage = new CustomerUpdatePage();
    expect(await customerUpdatePage.getPageTitle()).to.eq('storeApp.customer.home.createOrEditLabel');
    await customerUpdatePage.cancel();
  });

  /* it('should create and save Customers', async () => {
        const nbButtonsBeforeCreate = await customerComponentsPage.countDeleteButtons();

        await customerComponentsPage.clickOnCreateButton();
        await promise.all([
            customerUpdatePage.setErpIdInput('erpId'),
            customerUpdatePage.setName1Input('name1'),
            customerUpdatePage.setName2Input('name2'),
            customerUpdatePage.setName3Input('name3'),
            customerUpdatePage.setEmailInput('email'),
            customerUpdatePage.setPhoneInput('phone'),
            customerUpdatePage.setAddressLine1Input('addressLine1'),
            customerUpdatePage.setAddressLine2Input('addressLine2'),
            customerUpdatePage.setAddressLine3Input('addressLine3'),
            customerUpdatePage.setPlzInput('plz'),
            customerUpdatePage.setCityInput('city'),
            customerUpdatePage.setCountryInput('country'),
            customerUpdatePage.userSelectLastOption(),
        ]);
        expect(await customerUpdatePage.getErpIdInput()).to.eq('erpId', 'Expected ErpId value to be equals to erpId');
        expect(await customerUpdatePage.getName1Input()).to.eq('name1', 'Expected Name1 value to be equals to name1');
        expect(await customerUpdatePage.getName2Input()).to.eq('name2', 'Expected Name2 value to be equals to name2');
        expect(await customerUpdatePage.getName3Input()).to.eq('name3', 'Expected Name3 value to be equals to name3');
        expect(await customerUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
        expect(await customerUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');
        expect(await customerUpdatePage.getAddressLine1Input()).to.eq('addressLine1', 'Expected AddressLine1 value to be equals to addressLine1');
        expect(await customerUpdatePage.getAddressLine2Input()).to.eq('addressLine2', 'Expected AddressLine2 value to be equals to addressLine2');
        expect(await customerUpdatePage.getAddressLine3Input()).to.eq('addressLine3', 'Expected AddressLine3 value to be equals to addressLine3');
        expect(await customerUpdatePage.getPlzInput()).to.eq('plz', 'Expected Plz value to be equals to plz');
        expect(await customerUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
        expect(await customerUpdatePage.getCountryInput()).to.eq('country', 'Expected Country value to be equals to country');
        await customerUpdatePage.save();
        expect(await customerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Customer', async () => {
        const nbButtonsBeforeDelete = await customerComponentsPage.countDeleteButtons();
        await customerComponentsPage.clickOnLastDeleteButton();

        customerDeleteDialog = new CustomerDeleteDialog();
        expect(await customerDeleteDialog.getDialogTitle())
            .to.eq('storeApp.customer.delete.question');
        await customerDeleteDialog.clickOnConfirmButton();

        expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
