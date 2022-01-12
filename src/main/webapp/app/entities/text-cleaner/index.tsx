import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TextCleaner from './text-cleaner';
import TextCleanerDetail from './text-cleaner-detail';
import TextCleanerUpdate from './text-cleaner-update';
import TextCleanerDeleteDialog from './text-cleaner-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TextCleanerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TextCleanerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TextCleanerDetail} />
      <ErrorBoundaryRoute path={match.url} component={TextCleaner} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TextCleanerDeleteDialog} />
  </>
);

export default Routes;
