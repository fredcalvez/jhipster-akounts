import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankInstitution from './bank-institution';
import BankInstitutionDetail from './bank-institution-detail';
import BankInstitutionUpdate from './bank-institution-update';
import BankInstitutionDeleteDialog from './bank-institution-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankInstitutionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankInstitutionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankInstitutionDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankInstitution} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankInstitutionDeleteDialog} />
  </>
);

export default Routes;
