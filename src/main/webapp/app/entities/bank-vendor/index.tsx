import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankVendor from './bank-vendor';
import BankVendorDetail from './bank-vendor-detail';
import BankVendorUpdate from './bank-vendor-update';
import BankVendorDeleteDialog from './bank-vendor-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankVendorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankVendorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankVendorDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankVendor} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankVendorDeleteDialog} />
  </>
);

export default Routes;
