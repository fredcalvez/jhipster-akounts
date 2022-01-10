import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bank-account-interest.reducer';
import { IBankAccountInterest } from 'app/shared/model/bank-account-interest.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankAccountInterest = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bankAccountInterestList = useAppSelector(state => state.bankAccountInterest.entities);
  const loading = useAppSelector(state => state.bankAccountInterest.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bank-account-interest-heading" data-cy="BankAccountInterestHeading">
        <Translate contentKey="akountsApp.bankAccountInterest.home.title">Bank Account Interests</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.bankAccountInterest.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.bankAccountInterest.home.createLabel">Create new Bank Account Interest</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bankAccountInterestList && bankAccountInterestList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.interest">Interest</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.period">Period</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.interestType">Interest Type</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.units">Units</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.endDate">End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.scrappingURL">Scrapping URL</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.scrappingTag">Scrapping Tag</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.scrappingTagBis">Scrapping Tag Bis</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccountInterest.creditedAccountId">Credited Account Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bankAccountInterestList.map((bankAccountInterest, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bankAccountInterest.id}`} color="link" size="sm">
                      {bankAccountInterest.id}
                    </Button>
                  </td>
                  <td>{bankAccountInterest.interest}</td>
                  <td>{bankAccountInterest.period}</td>
                  <td>{bankAccountInterest.interestType}</td>
                  <td>{bankAccountInterest.units}</td>
                  <td>
                    {bankAccountInterest.startDate ? (
                      <TextFormat type="date" value={bankAccountInterest.startDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {bankAccountInterest.endDate ? (
                      <TextFormat type="date" value={bankAccountInterest.endDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{bankAccountInterest.scrappingURL}</td>
                  <td>{bankAccountInterest.scrappingTag}</td>
                  <td>{bankAccountInterest.scrappingTagBis}</td>
                  <td>
                    {bankAccountInterest.creditedAccountId ? (
                      <Link to={`bank-account/${bankAccountInterest.creditedAccountId.id}`}>
                        {bankAccountInterest.creditedAccountId.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bankAccountInterest.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bankAccountInterest.id}/edit`}
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
                        to={`${match.url}/${bankAccountInterest.id}/delete`}
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
              <Translate contentKey="akountsApp.bankAccountInterest.home.notFound">No Bank Account Interests found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BankAccountInterest;
