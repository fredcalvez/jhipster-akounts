import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './plaid-account.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaidAccountDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const plaidAccountEntity = useAppSelector(state => state.plaidAccount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plaidAccountDetailsHeading">
          <Translate contentKey="akountsApp.plaidAccount.detail.title">PlaidAccount</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.id}</dd>
          <dt>
            <span id="plaidAccountId">
              <Translate contentKey="akountsApp.plaidAccount.plaidAccountId">Plaid Account Id</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.plaidAccountId}</dd>
          <dt>
            <span id="itemId">
              <Translate contentKey="akountsApp.plaidAccount.itemId">Item Id</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.itemId}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="akountsApp.plaidAccount.type">Type</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.type}</dd>
          <dt>
            <span id="subtype">
              <Translate contentKey="akountsApp.plaidAccount.subtype">Subtype</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.subtype}</dd>
          <dt>
            <span id="balanceAvailable">
              <Translate contentKey="akountsApp.plaidAccount.balanceAvailable">Balance Available</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.balanceAvailable}</dd>
          <dt>
            <span id="balanceCurrent">
              <Translate contentKey="akountsApp.plaidAccount.balanceCurrent">Balance Current</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.balanceCurrent}</dd>
          <dt>
            <span id="isoCurrencyCode">
              <Translate contentKey="akountsApp.plaidAccount.isoCurrencyCode">Iso Currency Code</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.isoCurrencyCode}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="akountsApp.plaidAccount.name">Name</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.name}</dd>
          <dt>
            <span id="officialName">
              <Translate contentKey="akountsApp.plaidAccount.officialName">Official Name</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.officialName}</dd>
          <dt>
            <span id="iban">
              <Translate contentKey="akountsApp.plaidAccount.iban">Iban</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.iban}</dd>
          <dt>
            <span id="bic">
              <Translate contentKey="akountsApp.plaidAccount.bic">Bic</Translate>
            </span>
          </dt>
          <dd>{plaidAccountEntity.bic}</dd>
        </dl>
        <Button tag={Link} to="/plaid-account" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plaid-account/${plaidAccountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaidAccountDetail;
