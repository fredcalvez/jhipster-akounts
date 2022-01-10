import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './rebase-history.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RebaseHistoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const rebaseHistoryEntity = useAppSelector(state => state.rebaseHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rebaseHistoryDetailsHeading">
          <Translate contentKey="akountsApp.rebaseHistory.detail.title">RebaseHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rebaseHistoryEntity.id}</dd>
          <dt>
            <span id="oldValue">
              <Translate contentKey="akountsApp.rebaseHistory.oldValue">Old Value</Translate>
            </span>
          </dt>
          <dd>{rebaseHistoryEntity.oldValue}</dd>
          <dt>
            <span id="oldCurrency">
              <Translate contentKey="akountsApp.rebaseHistory.oldCurrency">Old Currency</Translate>
            </span>
          </dt>
          <dd>{rebaseHistoryEntity.oldCurrency}</dd>
          <dt>
            <span id="newValue">
              <Translate contentKey="akountsApp.rebaseHistory.newValue">New Value</Translate>
            </span>
          </dt>
          <dd>{rebaseHistoryEntity.newValue}</dd>
          <dt>
            <span id="newCurrency">
              <Translate contentKey="akountsApp.rebaseHistory.newCurrency">New Currency</Translate>
            </span>
          </dt>
          <dd>{rebaseHistoryEntity.newCurrency}</dd>
          <dt>
            <span id="runDate">
              <Translate contentKey="akountsApp.rebaseHistory.runDate">Run Date</Translate>
            </span>
          </dt>
          <dd>
            {rebaseHistoryEntity.runDate ? <TextFormat value={rebaseHistoryEntity.runDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/rebase-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rebase-history/${rebaseHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RebaseHistoryDetail;
