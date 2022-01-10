import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankStreamTransaction from './bank-stream-transaction';
import BankStreamTransactionDetail from './bank-stream-transaction-detail';
import BankStreamTransactionUpdate from './bank-stream-transaction-update';
import BankStreamTransactionDeleteDialog from './bank-stream-transaction-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankStreamTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankStreamTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankStreamTransactionDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankStreamTransaction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankStreamTransactionDeleteDialog} />
  </>
);

export default Routes;
