import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlaidTransaction from './plaid-transaction';
import PlaidTransactionDetail from './plaid-transaction-detail';
import PlaidTransactionUpdate from './plaid-transaction-update';
import PlaidTransactionDeleteDialog from './plaid-transaction-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaidTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaidTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaidTransactionDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlaidTransaction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlaidTransactionDeleteDialog} />
  </>
);

export default Routes;
