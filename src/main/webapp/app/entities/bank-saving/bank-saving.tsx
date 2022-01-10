import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bank-saving.reducer';
import { IBankSaving } from 'app/shared/model/bank-saving.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankSaving = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bankSavingList = useAppSelector(state => state.bankSaving.entities);
  const loading = useAppSelector(state => state.bankSaving.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bank-saving-heading" data-cy="BankSavingHeading">
        <Translate contentKey="akountsApp.bankSaving.home.title">Bank Savings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.bankSaving.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.bankSaving.home.createLabel">Create new Bank Saving</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bankSavingList && bankSavingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="akountsApp.bankSaving.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankSaving.summaryDate">Summary Date</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankSaving.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankSaving.goal">Goal</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankSaving.reach">Reach</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bankSavingList.map((bankSaving, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bankSaving.id}`} color="link" size="sm">
                      {bankSaving.id}
                    </Button>
                  </td>
                  <td>
                    {bankSaving.summaryDate ? <TextFormat type="date" value={bankSaving.summaryDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{bankSaving.amount}</td>
                  <td>{bankSaving.goal}</td>
                  <td>{bankSaving.reach}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bankSaving.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bankSaving.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bankSaving.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="akountsApp.bankSaving.home.notFound">No Bank Savings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BankSaving;
