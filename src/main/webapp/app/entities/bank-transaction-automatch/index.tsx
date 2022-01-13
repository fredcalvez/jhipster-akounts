import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankTransactionAutomatch from './bank-transaction-automatch';
import BankTransactionAutomatchDetail from './bank-transaction-automatch-detail';
import BankTransactionAutomatchUpdate from './bank-transaction-automatch-update';
import BankTransactionAutomatchDeleteDialog from './bank-transaction-automatch-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankTransactionAutomatchUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankTransactionAutomatchUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankTransactionAutomatchDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankTransactionAutomatch} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankTransactionAutomatchDeleteDialog} />
  </>
);

export default Routes;
