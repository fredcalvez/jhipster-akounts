import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlaidConfiguration from './plaid-configuration';
import PlaidConfigurationDetail from './plaid-configuration-detail';
import PlaidConfigurationUpdate from './plaid-configuration-update';
import PlaidConfigurationDeleteDialog from './plaid-configuration-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaidConfigurationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaidConfigurationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaidConfigurationDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlaidConfiguration} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlaidConfigurationDeleteDialog} />
  </>
);

export default Routes;
