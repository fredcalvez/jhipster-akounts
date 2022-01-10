import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './rebase-history.reducer';
import { IRebaseHistory } from 'app/shared/model/rebase-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RebaseHistory = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const rebaseHistoryList = useAppSelector(state => state.rebaseHistory.entities);
  const loading = useAppSelector(state => state.rebaseHistory.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="rebase-history-heading" data-cy="RebaseHistoryHeading">
        <Translate contentKey="akountsApp.rebaseHistory.home.title">Rebase Histories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.rebaseHistory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.rebaseHistory.home.createLabel">Create new Rebase History</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {rebaseHistoryList && rebaseHistoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="akountsApp.rebaseHistory.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.rebaseHistory.oldValue">Old Value</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.rebaseHistory.oldCurrency">Old Currency</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.rebaseHistory.newValue">New Value</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.rebaseHistory.newCurrency">New Currency</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.rebaseHistory.runDate">Run Date</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.rebaseHistory.transactionId">Transaction Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {rebaseHistoryList.map((rebaseHistory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${rebaseHistory.id}`} color="link" size="sm">
                      {rebaseHistory.id}
                    </Button>
                  </td>
                  <td>{rebaseHistory.oldValue}</td>
                  <td>
                    <Translate contentKey={`akountsApp.Currency.${rebaseHistory.oldCurrency}`} />
                  </td>
                  <td>{rebaseHistory.newValue}</td>
                  <td>
                    <Translate contentKey={`akountsApp.Currency.${rebaseHistory.newCurrency}`} />
                  </td>
                  <td>
                    {rebaseHistory.runDate ? <TextFormat type="date" value={rebaseHistory.runDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {rebaseHistory.transactionId ? (
                      <Link to={`bank-transaction/${rebaseHistory.transactionId.id}`}>{rebaseHistory.transactionId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${rebaseHistory.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rebaseHistory.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${rebaseHistory.id}/delete`}
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
              <Translate contentKey="akountsApp.rebaseHistory.home.notFound">No Rebase Histories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default RebaseHistory;
