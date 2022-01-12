import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import akountsSettings from 'app/entities/akounts-settings/akounts-settings.reducer';
// prettier-ignore
import automatch from 'app/entities/automatch/automatch.reducer';
// prettier-ignore
import bankCategory from 'app/entities/bank-category/bank-category.reducer';
// prettier-ignore
import bankAccount from 'app/entities/bank-account/bank-account.reducer';
// prettier-ignore
import bankInstitution from 'app/entities/bank-institution/bank-institution.reducer';
// prettier-ignore
import bankAccountInterest from 'app/entities/bank-account-interest/bank-account-interest.reducer';
// prettier-ignore
import bankTransaction from 'app/entities/bank-transaction/bank-transaction.reducer';
// prettier-ignore
import rebaseHistory from 'app/entities/rebase-history/rebase-history.reducer';
// prettier-ignore
import bankSaving from 'app/entities/bank-saving/bank-saving.reducer';
// prettier-ignore
import bankProject from 'app/entities/bank-project/bank-project.reducer';
// prettier-ignore
import bankProjectTransaction from 'app/entities/bank-project-transaction/bank-project-transaction.reducer';
// prettier-ignore
import bankStream from 'app/entities/bank-stream/bank-stream.reducer';
// prettier-ignore
import bankStreamTransaction from 'app/entities/bank-stream-transaction/bank-stream-transaction.reducer';
// prettier-ignore
import bankTag from 'app/entities/bank-tag/bank-tag.reducer';
// prettier-ignore
import bankTagTransaction from 'app/entities/bank-tag-transaction/bank-tag-transaction.reducer';
// prettier-ignore
import bankVendor from 'app/entities/bank-vendor/bank-vendor.reducer';
// prettier-ignore
import bankTransactionAutomatch from 'app/entities/bank-transaction-automatch/bank-transaction-automatch.reducer';
// prettier-ignore
import bridgeAccount from 'app/entities/bridge-account/bridge-account.reducer';
// prettier-ignore
import bridgeRun from 'app/entities/bridge-run/bridge-run.reducer';
// prettier-ignore
import bridgeTransaction from 'app/entities/bridge-transaction/bridge-transaction.reducer';
// prettier-ignore
import bridgeUser from 'app/entities/bridge-user/bridge-user.reducer';
// prettier-ignore
import budget from 'app/entities/budget/budget.reducer';
// prettier-ignore
import fileConfig from 'app/entities/file-config/file-config.reducer';
// prettier-ignore
import fileImport from 'app/entities/file-import/file-import.reducer';
// prettier-ignore
import plaidAccount from 'app/entities/plaid-account/plaid-account.reducer';
// prettier-ignore
import plaidConfiguration from 'app/entities/plaid-configuration/plaid-configuration.reducer';
// prettier-ignore
import plaidItem from 'app/entities/plaid-item/plaid-item.reducer';
// prettier-ignore
import plaidRun from 'app/entities/plaid-run/plaid-run.reducer';
// prettier-ignore
import plaidTransaction from 'app/entities/plaid-transaction/plaid-transaction.reducer';
// prettier-ignore
import processRun from 'app/entities/process-run/process-run.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  akountsSettings,
  automatch,
  bankCategory,
  bankAccount,
  bankInstitution,
  bankAccountInterest,
  bankTransaction,
  rebaseHistory,
  bankSaving,
  bankProject,
  bankProjectTransaction,
  bankStream,
  bankStreamTransaction,
  bankTag,
  bankTagTransaction,
  bankVendor,
  bankTransactionAutomatch,
  bridgeAccount,
  bridgeRun,
  bridgeTransaction,
  bridgeUser,
  budget,
  fileConfig,
  fileImport,
  plaidAccount,
  plaidConfiguration,
  plaidItem,
  plaidRun,
  plaidTransaction,
  processRun,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
