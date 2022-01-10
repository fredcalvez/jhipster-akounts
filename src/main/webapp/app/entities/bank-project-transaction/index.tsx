import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankProjectTransaction from './bank-project-transaction';
import BankProjectTransactionDetail from './bank-project-transaction-detail';
import BankProjectTransactionUpdate from './bank-project-transaction-update';
import BankProjectTransactionDeleteDialog from './bank-project-transaction-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankProjectTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankProjectTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankProjectTransactionDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankProjectTransaction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankProjectTransactionDeleteDialog} />
  </>
);

export default Routes;
