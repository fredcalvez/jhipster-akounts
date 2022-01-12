import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlaidAccount from './plaid-account';
import PlaidAccountDetail from './plaid-account-detail';
import PlaidAccountUpdate from './plaid-account-update';
import PlaidAccountDeleteDialog from './plaid-account-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaidAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaidAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaidAccountDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlaidAccount} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlaidAccountDeleteDialog} />
  </>
);

export default Routes;
