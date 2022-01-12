import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BridgeUser from './bridge-user';
import BridgeUserDetail from './bridge-user-detail';
import BridgeUserUpdate from './bridge-user-update';
import BridgeUserDeleteDialog from './bridge-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BridgeUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BridgeUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BridgeUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={BridgeUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BridgeUserDeleteDialog} />
  </>
);

export default Routes;
