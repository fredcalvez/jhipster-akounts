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
import Budget from './budget';
import FileConfig from './file-config';
import FileImport from './file-import';
import PlaidAccount from './plaid-account';
import PlaidConfiguration from './plaid-configuration';
import PlaidItem from './plaid-item';
import PlaidRun from './plaid-run';
import PlaidTransaction from './plaid-transaction';
import ProcessRun from './process-run';
import TextCleaner from './text-cleaner';
import TransactionDuplicates from './transaction-duplicates';
import TriggerRun from './trigger-run';
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
      <ErrorBoundaryRoute path={`${match.url}budget`} component={Budget} />
      <ErrorBoundaryRoute path={`${match.url}file-config`} component={FileConfig} />
      <ErrorBoundaryRoute path={`${match.url}file-import`} component={FileImport} />
      <ErrorBoundaryRoute path={`${match.url}plaid-account`} component={PlaidAccount} />
      <ErrorBoundaryRoute path={`${match.url}plaid-configuration`} component={PlaidConfiguration} />
      <ErrorBoundaryRoute path={`${match.url}plaid-item`} component={PlaidItem} />
      <ErrorBoundaryRoute path={`${match.url}plaid-run`} component={PlaidRun} />
      <ErrorBoundaryRoute path={`${match.url}plaid-transaction`} component={PlaidTransaction} />
      <ErrorBoundaryRoute path={`${match.url}process-run`} component={ProcessRun} />
      <ErrorBoundaryRoute path={`${match.url}text-cleaner`} component={TextCleaner} />
      <ErrorBoundaryRoute path={`${match.url}transaction-duplicates`} component={TransactionDuplicates} />
      <ErrorBoundaryRoute path={`${match.url}trigger-run`} component={TriggerRun} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
