import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankTagTransaction from './bank-tag-transaction';
import BankTagTransactionDetail from './bank-tag-transaction-detail';
import BankTagTransactionUpdate from './bank-tag-transaction-update';
import BankTagTransactionDeleteDialog from './bank-tag-transaction-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankTagTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankTagTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankTagTransactionDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankTagTransaction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankTagTransactionDeleteDialog} />
  </>
);

export default Routes;
