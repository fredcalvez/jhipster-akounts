import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AkountsSettings from './akounts-settings';
import AkountsSettingsDetail from './akounts-settings-detail';
import AkountsSettingsUpdate from './akounts-settings-update';
import AkountsSettingsDeleteDialog from './akounts-settings-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AkountsSettingsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AkountsSettingsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AkountsSettingsDetail} />
      <ErrorBoundaryRoute path={match.url} component={AkountsSettings} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AkountsSettingsDeleteDialog} />
  </>
);

export default Routes;
