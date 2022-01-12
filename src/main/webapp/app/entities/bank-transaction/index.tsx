import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankTransaction from './bank-transaction';
import BankTransactionDetail from './bank-transaction-detail';
import BankTransactionUpdate from './bank-transaction-update';
import BankTransactionDeleteDialog from './bank-transaction-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankTransactionDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankTransaction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankTransactionDeleteDialog} />
  </>
);

export default Routes;
