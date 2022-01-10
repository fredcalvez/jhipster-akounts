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
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
