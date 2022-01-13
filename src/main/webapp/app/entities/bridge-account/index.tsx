import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BridgeAccount from './bridge-account';
import BridgeAccountDetail from './bridge-account-detail';
import BridgeAccountUpdate from './bridge-account-update';
import BridgeAccountDeleteDialog from './bridge-account-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BridgeAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BridgeAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BridgeAccountDetail} />
      <ErrorBoundaryRoute path={match.url} component={BridgeAccount} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BridgeAccountDeleteDialog} />
  </>
);

export default Routes;
