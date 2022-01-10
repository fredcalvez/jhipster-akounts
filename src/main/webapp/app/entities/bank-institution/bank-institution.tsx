import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bank-institution.reducer';
import { IBankInstitution } from 'app/shared/model/bank-institution.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankInstitution = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bankInstitutionList = useAppSelector(state => state.bankInstitution.entities);
  const loading = useAppSelector(state => state.bankInstitution.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bank-institution-heading" data-cy="BankInstitutionHeading">
        <Translate contentKey="akountsApp.bankInstitution.home.title">Bank Institutions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.bankInstitution.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.bankInstitution.home.createLabel">Create new Bank Institution</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bankInstitutionList && bankInstitutionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="akountsApp.bankInstitution.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankInstitution.institutionLabel">Institution Label</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankInstitution.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankInstitution.active">Active</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankInstitution.currency">Currency</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankInstitution.isoCountryCode">Iso Country Code</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bankInstitutionList.map((bankInstitution, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bankInstitution.id}`} color="link" size="sm">
                      {bankInstitution.id}
                    </Button>
                  </td>
                  <td>{bankInstitution.institutionLabel}</td>
                  <td>{bankInstitution.code}</td>
                  <td>{bankInstitution.active ? 'true' : 'false'}</td>
                  <td>
                    <Translate contentKey={`akountsApp.Currency.${bankInstitution.currency}`} />
                  </td>
                  <td>{bankInstitution.isoCountryCode}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bankInstitution.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bankInstitution.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bankInstitution.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="akountsApp.bankInstitution.home.notFound">No Bank Institutions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BankInstitution;
