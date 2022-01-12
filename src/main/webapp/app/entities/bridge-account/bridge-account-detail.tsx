import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bridge-account.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BridgeAccountDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bridgeAccountEntity = useAppSelector(state => state.bridgeAccount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bridgeAccountDetailsHeading">
          <Translate contentKey="akountsApp.bridgeAccount.detail.title">BridgeAccount</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bridgeAccountEntity.id}</dd>
          <dt>
            <span id="bridgeAccountId">
              <Translate contentKey="akountsApp.bridgeAccount.bridgeAccountId">Bridge Account Id</Translate>
            </span>
          </dt>
          <dd>{bridgeAccountEntity.bridgeAccountId}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="akountsApp.bridgeAccount.type">Type</Translate>
            </span>
          </dt>
          <dd>{bridgeAccountEntity.type}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="akountsApp.bridgeAccount.status">Status</Translate>
            </span>
          </dt>
          <dd>{bridgeAccountEntity.status}</dd>
          <dt>
            <span id="balance">
              <Translate contentKey="akountsApp.bridgeAccount.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{bridgeAccountEntity.balance}</dd>
          <dt>
            <span id="isoCurrencyCode">
              <Translate contentKey="akountsApp.bridgeAccount.isoCurrencyCode">Iso Currency Code</Translate>
            </span>
          </dt>
          <dd>{bridgeAccountEntity.isoCurrencyCode}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="akountsApp.bridgeAccount.name">Name</Translate>
            </span>
          </dt>
          <dd>{bridgeAccountEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/bridge-account" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bridge-account/${bridgeAccountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BridgeAccountDetail;
