import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RebaseHistory from './rebase-history';
import RebaseHistoryDetail from './rebase-history-detail';
import RebaseHistoryUpdate from './rebase-history-update';
import RebaseHistoryDeleteDialog from './rebase-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RebaseHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RebaseHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RebaseHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={RebaseHistory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RebaseHistoryDeleteDialog} />
  </>
);

export default Routes;
