import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AkountsSettings from './akounts-settings';
import Automatch from './automatch';
import BankCategory from './bank-category';
import BankAccount from './bank-account';
import BankInstitution from './bank-institution';
import BankAccountInterest from './bank-account-interest';
import BankTransaction from './bank-transaction';
import RebaseHistory from './rebase-history';
import BankSaving from './bank-saving';
import BankProject from './bank-project';
import BankProjectTransaction from './bank-project-transaction';
import BankStream from './bank-stream';
import BankStreamTransaction from './bank-stream-transaction';
import BankTag from './bank-tag';
import BankTagTransaction from './bank-tag-transaction';
import BankVendor from './bank-vendor';
import BankTransactionAutomatch from './bank-transaction-automatch';
import BridgeAccount from './bridge-account';
import BridgeRun from './bridge-run';
import BridgeTransaction from './bridge-transaction';
import BridgeUser from './bridge-user';
import Bridge from './bridge';
import BankTransaction2 from './bank-transaction-2';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}akounts-settings`} component={AkountsSettings} />
      <ErrorBoundaryRoute path={`${match.url}automatch`} component={Automatch} />
      <ErrorBoundaryRoute path={`${match.url}bank-category`} component={BankCategory} />
      <ErrorBoundaryRoute path={`${match.url}bank-account`} component={BankAccount} />
      <ErrorBoundaryRoute path={`${match.url}bank-institution`} component={BankInstitution} />
      <ErrorBoundaryRoute path={`${match.url}bank-account-interest`} component={BankAccountInterest} />
      <ErrorBoundaryRoute path={`${match.url}bank-transaction`} component={BankTransaction} />
      <ErrorBoundaryRoute path={`${match.url}rebase-history`} component={RebaseHistory} />
      <ErrorBoundaryRoute path={`${match.url}bank-saving`} component={BankSaving} />
      <ErrorBoundaryRoute path={`${match.url}bank-project`} component={BankProject} />
      <ErrorBoundaryRoute path={`${match.url}bank-project-transaction`} component={BankProjectTransaction} />
      <ErrorBoundaryRoute path={`${match.url}bank-stream`} component={BankStream} />
      <ErrorBoundaryRoute path={`${match.url}bank-stream-transaction`} component={BankStreamTransaction} />
      <ErrorBoundaryRoute path={`${match.url}bank-tag`} component={BankTag} />
      <ErrorBoundaryRoute path={`${match.url}bank-tag-transaction`} component={BankTagTransaction} />
      <ErrorBoundaryRoute path={`${match.url}bank-vendor`} component={BankVendor} />
      <ErrorBoundaryRoute path={`${match.url}bank-transaction-automatch`} component={BankTransactionAutomatch} />
      <ErrorBoundaryRoute path={`${match.url}bridge-account`} component={BridgeAccount} />
      <ErrorBoundaryRoute path={`${match.url}bridge-run`} component={BridgeRun} />
      <ErrorBoundaryRoute path={`${match.url}bridge-transaction`} component={BridgeTransaction} />
      <ErrorBoundaryRoute path={`${match.url}bridge-user`} component={BridgeUser} />
      <ErrorBoundaryRoute path={`${match.url}bridge`} component={Bridge} />
      <ErrorBoundaryRoute path={`${match.url}bank-transaction-2`} component={BankTransaction2} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
