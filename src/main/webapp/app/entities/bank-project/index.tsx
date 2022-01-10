import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankProject from './bank-project';
import BankProjectDetail from './bank-project-detail';
import BankProjectUpdate from './bank-project-update';
import BankProjectDeleteDialog from './bank-project-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankProjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankProjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankProjectDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankProject} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankProjectDeleteDialog} />
  </>
);

export default Routes;
