import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FileImport from './file-import';
import FileImportDetail from './file-import-detail';
import FileImportUpdate from './file-import-update';
import FileImportDeleteDialog from './file-import-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FileImportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FileImportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FileImportDetail} />
      <ErrorBoundaryRoute path={match.url} component={FileImport} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FileImportDeleteDialog} />
  </>
);

export default Routes;
