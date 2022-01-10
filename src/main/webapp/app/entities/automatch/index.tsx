import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Automatch from './automatch';
import AutomatchDetail from './automatch-detail';
import AutomatchUpdate from './automatch-update';
import AutomatchDeleteDialog from './automatch-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AutomatchUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AutomatchUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AutomatchDetail} />
      <ErrorBoundaryRoute path={match.url} component={Automatch} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AutomatchDeleteDialog} />
  </>
);

export default Routes;
