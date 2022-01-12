import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bridge from './bridge';
import BridgeDetail from './bridge-detail';
import BridgeUpdate from './bridge-update';
import BridgeDeleteDialog from './bridge-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BridgeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BridgeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BridgeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Bridge} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BridgeDeleteDialog} />
  </>
);

export default Routes;
