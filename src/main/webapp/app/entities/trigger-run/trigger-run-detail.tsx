import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './trigger-run.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TriggerRunDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const triggerRunEntity = useAppSelector(state => state.triggerRun.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="triggerRunDetailsHeading">
          <Translate contentKey="akountsApp.triggerRun.detail.title">TriggerRun</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{triggerRunEntity.id}</dd>
          <dt>
            <span id="runType">
              <Translate contentKey="akountsApp.triggerRun.runType">Run Type</Translate>
            </span>
          </dt>
          <dd>{triggerRunEntity.runType}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="akountsApp.triggerRun.status">Status</Translate>
            </span>
          </dt>
          <dd>{triggerRunEntity.status}</dd>
          <dt>
            <span id="addDate">
              <Translate contentKey="akountsApp.triggerRun.addDate">Add Date</Translate>
            </span>
          </dt>
          <dd>{triggerRunEntity.addDate ? <TextFormat value={triggerRunEntity.addDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="akountsApp.triggerRun.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {triggerRunEntity.startDate ? <TextFormat value={triggerRunEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="akountsApp.triggerRun.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>{triggerRunEntity.endDate ? <TextFormat value={triggerRunEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/trigger-run" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trigger-run/${triggerRunEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TriggerRunDetail;
