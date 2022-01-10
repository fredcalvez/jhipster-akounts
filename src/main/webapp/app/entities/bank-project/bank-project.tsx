import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bank-project.reducer';
import { IBankProject } from 'app/shared/model/bank-project.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankProject = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bankProjectList = useAppSelector(state => state.bankProject.entities);
  const loading = useAppSelector(state => state.bankProject.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bank-project-heading" data-cy="BankProjectHeading">
        <Translate contentKey="akountsApp.bankProject.home.title">Bank Projects</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.bankProject.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.bankProject.home.createLabel">Create new Bank Project</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bankProjectList && bankProjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="akountsApp.bankProject.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankProject.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankProject.projectType">Project Type</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankProject.initialValue">Initial Value</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankProject.currentValue">Current Value</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankProject.mainAccountId">Main Account Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bankProjectList.map((bankProject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bankProject.id}`} color="link" size="sm">
                      {bankProject.id}
                    </Button>
                  </td>
                  <td>{bankProject.name}</td>
                  <td>{bankProject.projectType}</td>
                  <td>{bankProject.initialValue}</td>
                  <td>{bankProject.currentValue}</td>
                  <td>
                    {bankProject.mainAccountId ? (
                      <Link to={`bank-account/${bankProject.mainAccountId.id}`}>{bankProject.mainAccountId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bankProject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bankProject.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bankProject.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="akountsApp.bankProject.home.notFound">No Bank Projects found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BankProject;
