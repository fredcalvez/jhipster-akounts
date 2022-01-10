import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bank-stream.reducer';
import { IBankStream } from 'app/shared/model/bank-stream.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankStream = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bankStreamList = useAppSelector(state => state.bankStream.entities);
  const loading = useAppSelector(state => state.bankStream.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bank-stream-heading" data-cy="BankStreamHeading">
        <Translate contentKey="akountsApp.bankStream.home.title">Bank Streams</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.bankStream.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.bankStream.home.createLabel">Create new Bank Stream</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bankStreamList && bankStreamList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="akountsApp.bankStream.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankStream.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankStream.streamType">Stream Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bankStreamList.map((bankStream, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bankStream.id}`} color="link" size="sm">
                      {bankStream.id}
                    </Button>
                  </td>
                  <td>{bankStream.name}</td>
                  <td>{bankStream.streamType}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bankStream.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bankStream.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bankStream.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="akountsApp.bankStream.home.notFound">No Bank Streams found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BankStream;
