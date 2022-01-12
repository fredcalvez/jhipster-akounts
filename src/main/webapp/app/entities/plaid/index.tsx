import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Plaid from './plaid';
import PlaidDetail from './plaid-detail';
import PlaidUpdate from './plaid-update';
import PlaidDeleteDialog from './plaid-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaidUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaidUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaidDetail} />
      <ErrorBoundaryRoute path={match.url} component={Plaid} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlaidDeleteDialog} />
  </>
);

export default Routes;
